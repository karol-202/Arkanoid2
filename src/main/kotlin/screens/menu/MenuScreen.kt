package screens.menu

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.arkade.htmlcanvas.assets.Image
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.group
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.imageDraw
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.textFill
import pl.karol202.uranium.arkade.htmlcanvas.values.*
import pl.karol202.uranium.core.common.AutoKey
import ui.menuBottomRight
import ui.menuButtons
import ui.menuCenter

fun ArkadeRenderScope.menuScreen(key: Any = AutoKey,
                                 size: Vector,
                                 onGameStart: () -> Unit) =
		group(key = key) {
			+ background(size = size)
			+ menuCenter(size = size) {
				+ title()
				+ menuButtons(buttons = listOf("Start" to onGameStart))
			}
			+ menuBottomRight(key = "bottom_right",
			                  size = size) {
				+ authorLabel()
			}
		}

private fun ArkadeRenderScope.background(key: Any = AutoKey,
                                         size: Vector) =
		imageDraw(key = key,
		          image = Image.load("menu_background.png"),
		          drawBounds = Bounds(size = size))

private fun ArkadeRenderScope.title(key: Any = AutoKey) =
		textFill(key = key,
		         position = Vector(y = -100.0),
		         text = "Arkanoid",
		         font = Font.create(72, "monospace"),
		         fillStyle = Color.raw("white"),
		         align = TextAlign.CENTER)

private fun ArkadeRenderScope.authorLabel(key: Any = AutoKey) =
		textFill(key = key,
		         position = Vector(x = -20.0, y = -10.0),
		         text = "by Karol Jurski",
		         font = Font.create(15, "monospace"),
		         fillStyle = Color.raw("white"),
		         align = TextAlign.END,
		         baseline = TextBaseline.BOTTOM)
