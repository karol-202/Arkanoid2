package ui

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.primitives.TextAlign
import pl.karol202.uranium.webcanvas.component.primitives.TextBaseline
import pl.karol202.uranium.webcanvas.component.primitives.pathFill
import pl.karol202.uranium.webcanvas.component.primitives.textFill
import pl.karol202.uranium.webcanvas.component.ui.button
import pl.karol202.uranium.webcanvas.component.ui.stack
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Font
import pl.karol202.uranium.webcanvas.values.Font.Companion
import pl.karol202.uranium.webcanvas.values.Path
import pl.karol202.uranium.webcanvas.values.Vector

private const val BUTTON_OFFSET_Y = 100.0
private val BUTTON_SIZE = Vector(280.0, 70.0)
private const val BUTTON_SHAPE_SVG =
		"M 0 0 L 0 46.248047 L 23.751953 70 L 280 70 L 280 24.160156 L 255.83984 0" +
		"L 0 0 z M 4 4 L 251.83984 4 L 276 26.160156 L 276 66 L 27.751953 66 L 4 42.248047 L 4 4 z"

fun WCRenderScope.menuButtons(key: Any = AutoKey,
                                      buttons: List<Pair<String, () -> Unit>>) =
		menuButtonsPanel(key = key) {
			+ stack(elements = buttons,
			        elementOffset = Vector(y = BUTTON_OFFSET_Y)) { (text, onClick), _ ->
				+ menuButton(text = text,
				             onClick = onClick)
			}
		}

private fun WCRenderScope.menuButtonsPanel(key: Any = AutoKey,
                                           content: WCRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = Vector(x = -BUTTON_SIZE.x / 2),
		          content = content)

private fun WCRenderScope.menuButton(key: Any = AutoKey,
                                     text: String,
                                     onClick: () -> Unit) =
		button(key = key,
		       size = BUTTON_SIZE,
		       idleContent = {
			       + menuButtonContent(color = Color.hsl(235, 1.0, 0.37),
			                           text = text)
		       },
		       hoverContent = {
			       + menuButtonContent(color = Color.hsl(235, 1.0, 0.55),
			                           text = text)
		       },
		       clickContent = {
			       + menuButtonContent(color = Color.hsl(235, 1.0, 0.7),
			                           text = text)
		       },
		       onClick = onClick)

private fun WCRenderScope.menuButtonContent(key: Any = AutoKey,
                                            color: Color,
                                            text: String) =
		group(key = key) {
			+ pathFill(path = Path.fromSVG(BUTTON_SHAPE_SVG),
			           fillStyle = color)
			+ textFill(key = "text",
			           position = Vector(x = BUTTON_SIZE.x / 2, y = BUTTON_SIZE.y / 2),
			           text = text,
			           font = Font.create(30, "monospace"),
			           fillStyle = color,
			           align = TextAlign.CENTER,
			           baseline = TextBaseline.MIDDLE)
		}
