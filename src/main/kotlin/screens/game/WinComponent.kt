package screens.game

import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.values.Vector

fun WCRenderScope.winComponent(size: Vector,
                               gameState: GameState.Win,
                               onStartAgain: () -> Unit) =
		group {
			+ paddleMovement(screenSize = size,
			                 positionX = gameState.paddleX,
			                 onPositionChange = { }) {
				+ paddle()
			}
		}
