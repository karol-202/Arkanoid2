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

fun ArkadeRenderScope.gameOverComponent(key: Any = AutoKey,
                                        size: Vector,
                                        gameState: GameState.GameOver,
                                        onTryAgain: () -> Unit) =
		group(key = key) {
			+ gameState.bricks.map {
				brick(it)
			}
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
					+ gameOverText(key = "game_over")
					+ menuButtons(buttons = listOf("Jeszcze raz" to onTryAgain))
				}
			}
		}

private fun ArkadeRenderScope.gameOverText(key: Any = AutoKey) =
		textFill(key = key,
		         position = Vector(y = -100.0),
		         text = "Game over!",
		         font = Font.create(72, "monospace"),
		         fillStyle = Color.raw("white"),
		         align = TextAlign.CENTER)
