#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.common.UProps
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
#parse("File Header.java")
class ${NAME}(props: Props) : WCAbstractComponent<${NAME}.Props>(props)
{
	data class Props(override val key: Any) : UProps

	override fun WCRenderBuilder.render()
	{

	}
}

#set ($FUNNAME = $NAME.substring(0,1).toLowerCase() + $NAME.substring(1))
fun WCRenderScope.$FUNNAME(key: Any = AutoKey) = component(::${NAME}, ${NAME}.Props(key))
