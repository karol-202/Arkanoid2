package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderBuilder
import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.arkade.htmlcanvas.assets.Image
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.group
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.translate
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.collider.colliderProvider
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.imageDraw
import pl.karol202.uranium.arkade.htmlcanvas.physics.collider.RectCollider
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.render.render

val PADDLE_SIZE = Vector(100.0, 20.0)
const val PADDLE_BASELINE_BOTTOM_Y = 50.0

fun ArkadeRenderScope.paddleMovement(key: Any = AutoKey,
                                     screenSize: Vector,
                                     positionX: Double,
                                     onPositionChange: (Double) -> Unit,
                                     content: ArkadeRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = Vector(y = screenSize.y - PADDLE_BASELINE_BOTTOM_Y)) {
			+ mouseFollower(currentX = positionX,
			                onMove = onPositionChange) {
				+ content.render()
			}
		}

fun ArkadeRenderScope.paddle(key: Any = AutoKey) =
		group(key = key) {
			val bounds = Bounds(start = Vector(x = -PADDLE_SIZE.x / 2), size = PADDLE_SIZE)
			+ colliderProvider(collider = RectCollider(bounds = bounds,
			                                           payload = ColliderType.Paddle))
			+ imageDraw(image = Image.load("paddle.png"),
			            drawBounds = bounds)
		}
