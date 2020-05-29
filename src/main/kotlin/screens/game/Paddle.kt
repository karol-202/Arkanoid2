package screens.game

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.render.render
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.assets.loadImage
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.physics.collider.collider
import pl.karol202.uranium.webcanvas.component.primitives.image
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.physics.collider.RectCollider
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector

val PADDLE_SIZE = Vector(100.0, 20.0)
const val PADDLE_BASELINE_BOTTOM_Y = 50.0

fun WCRenderScope.paddleMovement(key: Any = AutoKey,
                                 screenSize: Vector,
                                 positionX: Double,
                                 onPositionChange: (Double) -> Unit,
                                 content: WCRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = Vector(y = screenSize.y - PADDLE_BASELINE_BOTTOM_Y)) {
			+ mouseFollower(currentX = positionX,
			                onMove = onPositionChange) {
				+ content.render()
			}
		}

fun WCRenderScope.paddle(key: Any = AutoKey) =
		group(key = key) {
			val bounds = Bounds(start = Vector(x = -PADDLE_SIZE.x / 2), size = PADDLE_SIZE)
			+ collider(collider = RectCollider(bounds = bounds,
			                                   payload = ColliderType.Paddle))
			+ image(image = loadImage("paddle.png"),
			        drawBounds = bounds)
		}
