package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.assets.Image
import pl.karol202.uranium.arkade.htmlcanvas.physics.collider.Collider
import pl.karol202.uranium.arkade.htmlcanvas.physics.collider.RectCollider
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector

sealed class Level
{
	object Level1 : Level()
	{
		private const val BRICK_WIDTH_HEIGHT_RATIO = 3.0

		override val name = "Poziom 1"
		override val nextLevel = Level2
		override val initialHp = 3

		override fun generateBricks(screenSize: Vector) = generateBricksGrid(screenSize = screenSize,
		                                                                     widthHeightRatio = BRICK_WIDTH_HEIGHT_RATIO,
		                                                                     rows = 3,
		                                                                     columns = 4) { _, _, id, collider, bounds ->
			Brick(id = id,
			      collider = collider,
			      bounds = bounds,
			      hpImageMap = mapOf(1 to Image.load("brick_green.png")),
			      hp = 1)
		}
	}

	object Level2 : Level()
	{
		private const val BRICK_WIDTH_HEIGHT_RATIO = 2.0

		override val name = "Poziom 2"
		override val nextLevel = Level3
		override val initialHp = 3

		override fun generateBricks(screenSize: Vector) = generateBricksGrid(screenSize = screenSize,
		                                                                     widthHeightRatio = BRICK_WIDTH_HEIGHT_RATIO,
		                                                                     rows = 3,
		                                                                     columns = 7) { _, y, id, collider, bounds ->
			Brick(id = id,
			      collider = collider,
			      bounds = bounds,
			      hpImageMap = mapOf(1 to Image.load("brick_green.png"),
			                         2 to Image.load("brick_yellow.png")),
			      hp = if(y == 1) 2 else 1)
		}
	}

	object Level3 : Level()
	{
		private const val BRICK_WIDTH_HEIGHT_RATIO = 2.0

		override val name = "Poziom 3"
		override val nextLevel = WinLevel
		override val initialHp = 3
		override val isVerticallyInverted = true

		override fun generateBricks(screenSize: Vector) = generateBricksGrid(screenSize = screenSize,
		                                                                     widthHeightRatio = BRICK_WIDTH_HEIGHT_RATIO,
		                                                                     rows = 4,
		                                                                     columns = 8) { _, y, id, collider, bounds ->
			Brick(id = id,
			      collider = collider,
			      bounds = bounds,
			      hpImageMap = mapOf(1 to Image.load("brick_green.png"),
			                         2 to Image.load("brick_yellow.png"),
			                         3 to Image.load("brick_red.png")),
			      hp = when(y)
			      {
				      0 -> 2
				      3 -> 3
				      else -> 1
			      })
		}
	}

	object WinLevel : Level()
	{
		override val name = ""
		override val nextLevel = this
		override val initialHp: Int? = null

		override fun generateBricks(screenSize: Vector) = emptyList<Brick>()

		override fun isCompleted(bricks: List<Brick>) = false
	}

	abstract val name: String
	abstract val nextLevel: Level
	abstract val initialHp: Int?
	open val isVerticallyInverted = false

	abstract fun generateBricks(screenSize: Vector): List<Brick>

	open fun isCompleted(bricks: List<Brick>) = bricks.isEmpty()
}

private fun generateBricksGrid(screenSize: Vector, widthHeightRatio: Double,
                               rows: Int, columns: Int,
                               brickProvider: (x: Int, y: Int, id: String, collier: Collider, bound: Bounds) -> Brick): List<Brick>
{
	val brickWidth = screenSize.x / columns
	val brickHeight = brickWidth / widthHeightRatio
	val brickSize = Vector(brickWidth, brickHeight)
	return (0 until rows).flatMap { row ->
		(0 until columns).map { column ->
			val id = "${column}x${row}"
			val bounds = Bounds(start = Vector(x = column * brickWidth, y = row * brickHeight),
			                    size = brickSize)
			val collider = RectCollider(bounds = bounds,
			                            payload = ColliderType.Brick(id))
			brickProvider(column, row, id, collider, bounds)
		}
	}
}
