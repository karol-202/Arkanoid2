package screens.game

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.primitives.TextAlign
import pl.karol202.uranium.webcanvas.component.primitives.textFill
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Font
import pl.karol202.uranium.webcanvas.values.Vector
import ui.menuBackground
import ui.menuButtons
import ui.menuCenter

fun WCRenderScope.winComponent(key: Any = AutoKey,
                               size: Vector,
                               gameState: GameState.Win,
                               onStartAgain: () -> Unit) =
		group(key = key) {
			+ paddleMovement(screenSize = size,
			                 positionX = gameState.paddleX,
			                 onPositionChange = { }) {
				+ paddle()
			}
			+ menuBackground(size = size)
			+ menuCenter(key = "menu_center",
			             size = size) {
				+ winText(key = "win")
				+ menuButtons(buttons = listOf("Jeszcze raz" to onStartAgain))
			}
		}

private fun WCRenderScope.winText(key: Any = AutoKey) =
		textFill(key = key,
		         position = Vector(y = -100.0),
		         text = "Wygrałeś",
		         font = Font.create(72, "monospace"),
		         fillStyle = Color.raw("white"),
		         align = TextAlign.CENTER)
