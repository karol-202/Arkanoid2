package screens.game

import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.event.eventHandler
import pl.karol202.uranium.webcanvas.values.InputEvent
import pl.karol202.uranium.webcanvas.values.Vector

fun WCRenderScope.prepareStateComponent(size: Vector,
                                        gameState: GameState.Prepare,
                                        onPaddlePositionChange: (Double) -> Unit,
                                        onStart: () -> Unit) =
		group {
			+ startTrigger(onStart = onStart)
			+ gameState.bricks.map {
				brick(it)
			}
			+ paddleMovement(screenSize = size,
			                 positionX = gameState.paddleX,
			                 onPositionChange = onPaddlePositionChange) {
				+ paddle()
				+ translate(vector = Vector(y = -BALL_RADIUS)) {
					+ ball()
				}
			}
		}

private fun WCRenderScope.startTrigger(onStart: () -> Unit) =
		eventHandler(mouseListener = { handleEvent(it, onStart) })

private fun handleEvent(event: InputEvent.Mouse, onStart: () -> Unit)
{
	if(event.type == InputEvent.Mouse.Type.UP) onStart()
}
