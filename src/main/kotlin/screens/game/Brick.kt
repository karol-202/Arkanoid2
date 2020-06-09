package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.arkade.htmlcanvas.assets.Image
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.group
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.collider.colliderProvider
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.imageDraw
import pl.karol202.uranium.arkade.htmlcanvas.physics.collider.Collider
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds

fun ArkadeRenderScope.brick(brick: Brick) =
		group(key = brick.id) {
			+ colliderProvider(collider = brick.collider)
			+ imageDraw(image = brick.image,
			            drawBounds = brick.bounds)
		}

data class Brick(val id: String,
                 val collider: Collider,
                 val bounds: Bounds,
                 val hpImageMap: Map<Int, Image>,
                 val hp: Int)
{
	val image get() = hpImageMap[hp] ?: throw IllegalStateException("No color defined for hp: $hp")

	fun hit() = if(hp > 1) copy(hp = hp - 1) else null
}
