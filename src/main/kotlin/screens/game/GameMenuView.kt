package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.group
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.translate
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector
import ui.menuBackground
import ui.menuButtons
import ui.menuCenter

private const val MENU_CONTENT_OFFSET_Y = -100.0

fun ArkadeRenderScope.gameMenuView(key: Any = AutoKey,
                                   size: Vector,
                                   onBackToGame: () -> Unit,
                                   onQuit: () -> Unit) =
		group(key = key) {
			+ menuBackground(size = size)
			+ menuCenter(size = size) {
				+ translate(vector = Vector(y = MENU_CONTENT_OFFSET_Y)) {
					+ menuButtons(buttons = listOf("Powrót do gry" to onBackToGame,
					                               "Menu główne" to onQuit))
				}
			}
		}
