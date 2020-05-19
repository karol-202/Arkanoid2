import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.draw.size
import pl.karol202.uranium.webcanvas.draw.startOnCanvas
import pl.karol202.uranium.webcanvas.values.Vector
import screens.game.gameScreen
import kotlin.browser.document

private val canvas = document.body!!.append.canvas { }

fun main() = startOnCanvas(canvas, renderInterval = 20, physicsInterval = 20) { app(size = canvas.size) }

class App(props: Props) : WCAbstractComponent<App.Props>(props)
{
    data class Props(override val key: Any,
                     val size: Vector) : UProps

    override fun WCRenderBuilder.render()
    {
        + gameScreen(size = props.size)
    }
}

fun WCRenderScope.app(key: Any = AutoKey,
                      size: Vector) = component(::App, App.Props(key, size))
