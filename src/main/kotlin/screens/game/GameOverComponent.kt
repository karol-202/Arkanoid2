package screens.game

import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.values.Vector

fun WCRenderScope.gameOverComponent(size: Vector,
                                    gameState: GameState.GameOver,
                                    onTryAgain: () -> Unit) =
		group {
			+ gameState.bricks.map {
				brick(it)
			}
			+ paddleMovement(screenSize = size,
			                 positionX = gameState.paddleX,
			                 onPositionChange = { }) {
				+ paddle()
			}
		}
