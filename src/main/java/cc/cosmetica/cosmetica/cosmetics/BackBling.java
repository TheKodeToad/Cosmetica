/*
 * Copyright 2022 EyezahMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.cosmetica.cosmetica.cosmetics;

import cc.cosmetica.api.Model;
import cc.cosmetica.cosmetica.Cosmetica;
import cc.cosmetica.cosmetica.cosmetics.model.BakableModel;
import cc.cosmetica.cosmetica.cosmetics.model.CosmeticStack;
import cc.cosmetica.cosmetica.screens.fakeplayer.FakePlayer;
import cc.cosmetica.cosmetica.screens.fakeplayer.MenuRenderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.Items;

public class BackBling<T extends AbstractClientPlayer> extends CustomLayer<T, PlayerModel<T>> implements MenuRenderLayer {
	public BackBling(RenderLayerParent<T, PlayerModel<T>> renderLayerParent) {
		super(renderLayerParent);
	}

	@Override
	public void render(PoseStack stack, MultiBufferSource multiBufferSource, int packedLightProbably, T player, float f, float g, float pitch, float j, float k, float l) {
		if (player.isInvisible()) return;
		BakableModel modelData = this.canOverridePlayerCosmetics(player) ? OVERRIDDEN.get(() -> Cosmetica.getPlayerData(player).backBling()) : Cosmetica.getPlayerData(player).backBling();

		if (modelData == null) return; // if it has a model
		if (((player.isCapeLoaded() && player.isModelPartShown(PlayerModelPart.CAPE) && player.getCloakTextureLocation() != null) || player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA))
				&& (modelData.extraInfo() & Model.SHOW_BACK_BLING_WITH_CAPE) == 0) return; // if wearing cape/elytra and show bb w cape is not set
		else if ((player.hasItemInSlot(EquipmentSlot.CHEST) && !player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA)) && (modelData.extraInfo() & Model.SHOW_BACK_BLING_WITH_CHESTPLATE) == 0) return; // if wearing chestplate and show bb w chestplate is not set

		stack.pushPose();
		doCoolRenderThings(modelData, this.getParentModel().body, stack, multiBufferSource, packedLightProbably, 0, -0.1f - (0.15f/6.0f), 0.1f + (0.4f/16.0f));
		stack.popPose();
	}

	@Override
	public void render(PoseStack stack, MultiBufferSource bufferSource, int packedLight, FakePlayer player, float o, float n, float delta, float bob, float yRotDiff, float xRot) {
		BakableModel modelData = OVERRIDDEN.get(() -> player.getData().backBling());

		if (modelData == null) return; // if it has a model
		if (player.getData().cape().getImage() != null && (modelData.extraInfo() & Model.SHOW_BACK_BLING_WITH_CAPE) == 0) return; // if wearing cape and show bb w cape is not set

		stack.pushPose();
		doCoolRenderThings(modelData, this.getParentModel().body, stack, bufferSource, packedLight, 0, -0.1f - (0.15f/6.0f), 0.1f);
		stack.popPose();
	}

	public static final CosmeticStack<BakableModel> OVERRIDDEN = new CosmeticStack();
}
