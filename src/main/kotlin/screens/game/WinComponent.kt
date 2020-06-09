package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.flip
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.group
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.textFill
import pl.karol202.uranium.arkade.htmlcanvas.values.Color
import pl.karol202.uranium.arkade.htmlcanvas.values.Font
import pl.karol202.uranium.arkade.htmlcanvas.values.TextAlign
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector
import ui.menuBackground
import ui.menuButtons
import ui.menuCenter

fun ArkadeRenderScope.winComponent(key: Any = AutoKey,
                                   size: Vector,
                                   gameState: GameState.Win,
                                   onStartAgain: () -> Unit) =
		group(key = key) {
			+ paddleMovement(screenSize = size,
			                 positionX = gameState.paddleX,
			                 onPositionChange = { }) {
				+ paddle()
			}

			+ flip(key = "menu",
			       size = size,
			       vertical = gameState.level.isVerticallyInverted /* To compensate previous flip */) {
				+ menuBackground(size = size)
				+ menuCenter(key = "menu_center",
				             size = size) {
					+ winText(key = "win")
					+ menuButtons(buttons = listOf("Jeszcze raz" to onStartAgain))
				}
			}
		}

private fun ArkadeRenderScope.winText(key: Any = AutoKey) =
		textFill(key = key,
		         position = Vector(y = -100.0),
		         text = "Wygrałeś",
		         font = Font.create(72, "monospace"),
		         fillStyle = Color.raw("white"),
		         align = TextAlign.CENTER)
