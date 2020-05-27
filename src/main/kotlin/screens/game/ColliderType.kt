package screens.game

interface ColliderType
{
	object Paddle : ColliderType

	data class Brick(val id: String) : ColliderType

	object ScreenEdge : ColliderType

	object DeathEdge : ColliderType
}
