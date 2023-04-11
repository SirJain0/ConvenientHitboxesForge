package net.sirjain.convenient_hitboxes.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public class ControlsScreenMixin extends Screen {
    protected ControlsScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        if (this.minecraft == null) return;

        // Adds the button
        this.addRenderableWidget(
            Button.builder(
                !this.minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes() ? Component.translatable("gui.entity_hitbox.disabled") : Component.translatable("gui.entity_hitbox.enabled"),
                (p_96278_) -> {
                    this.minecraft.getEntityRenderDispatcher().setRenderHitBoxes(!this.minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes());
                    p_96278_.setMessage(!this.minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes() ? Component.translatable("gui.entity_hitbox.disabled") : Component.translatable("gui.entity_hitbox.enabled"));
                    this.debugFeedbackTranslated(!this.minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes() ? "debug.show_hitboxes.off" : "debug.show_hitboxes.on");
                }
            )
            .bounds(this.width / 2 - 75, this.height / 2 + 20, 150, 20)
            .build()
        );
    }

    private void debugComponent(ChatFormatting p_167825_, Component p_167826_) {
        if (this.minecraft == null) return;
        this.minecraft.gui.getChat().addMessage(Component.empty().append(Component.translatable("debug.prefix").withStyle(p_167825_, ChatFormatting.BOLD)).append(" ").append(p_167826_));
    }

    private void debugFeedbackComponent(Component p_167823_) {
        this.debugComponent(ChatFormatting.YELLOW, p_167823_);
    }

    private void debugFeedbackTranslated(String p_90914_, Object... p_90915_) {
        this.debugFeedbackComponent(Component.translatable(p_90914_, p_90915_));
    }
}