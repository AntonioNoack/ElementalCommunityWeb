package android.graphics

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Matrix constructor(var data: Array<FloatArray>) {

	constructor(): this(arrayOf(FloatArray(3), FloatArray(3), FloatArray(3)))

	private constructor(dimension: Int = 4, unitNotZero: Boolean) : this(when (unitNotZero) {
		true -> Array(dimension) { index -> FloatArray(dimension) { index2 -> if (index == index2) 1f else 0f } }
		else -> Array(dimension) { FloatArray(dimension) { 0f } }
	})

	private constructor(m: Int, n: Int, unitNotZero: Boolean) : this(when (unitNotZero) {
		true -> Array(m) { index -> FloatArray(n) { index2 -> if (index == index2) 1f else 0f } }
		else -> Array(m) { FloatArray(n) { 0f } }
	})

	init {
		/*if(data.size == 4 && data[0].size == 4){
            markForReuse(this)
        }*/
	}

	operator fun times(matrix: Matrix): Matrix {
		multiply(matrix)
		return this
	}

	operator fun timesAssign(matrix: Matrix) {
		multiply(matrix)
	}

	fun getX(x: Float = 0f, y: Float = 0f, z: Float = 1f, w: Float = 1f): Float {
		return get(data[0], x, y, z, w)
	}

	fun getY(x: Float = 0f, y: Float = 0f, z: Float = 1f, w: Float = 1f): Float {
		return get(data[1], x, y, z, w)
	}

	fun getZ(x: Float = 0f, y: Float = 0f, z: Float = 1f, w: Float = 1f): Float {
		return get(data[2], x, y, z, w)
	}

	fun get(row: FloatArray, x: Float = 0f, y: Float = 0f, z: Float = 1f, w: Float = 1f): Float {
		return row[0] * x + row[1] * y + row[2] * z + if (row.size > 3) row[3] * w else 0f
	}

	fun multiply(matrix: Matrix): Matrix {

		// save into this matrix...
		val sec = matrix.data
		if (sec.size != data[0].size) {
			throw RuntimeException("Can't multiply ${data.size} x ${data[0].size} times ${sec.size} x ${sec[0].size} matrix!")
		}

		val n = sec.size
		data = Array(data.size) { i ->
			val slice = data[i]
			FloatArray(sec[0].size) { j ->
				var sum = slice[0] * sec[0][j]
				for (k in 1 until n) {
					sum += slice[k] * sec[k][j]
				}
				sum
			}
		}

		return this
	}

	fun rotateX(alpha: Float): Matrix {
		if (alpha != 0f) {
			val sin = sin(alpha)
			val cos = cos(alpha)
			data.forEach { array ->
				val a = array[1]
				val b = array[2]
				array[1] = cos * a - sin * b
				array[2] = sin * a + cos * b
			}
		}
		return this
	}

	fun rotateY(alpha: Float): Matrix {
		if (alpha != 0f) {
			val sin = sin(alpha)
			val cos = cos(alpha)
			data.forEach { array ->
				val a = array[0]
				val b = array[2]
				array[0] = cos * a + sin * b
				array[2] = -sin * a + cos * b
			}
		}
		return this
	}

	fun rotateZ(alpha: Float): Matrix {
		if (alpha != 0f) {
			val sin = sin(alpha)
			val cos = cos(alpha)
			data.forEach { array ->
				val a = array[0]
				val b = array[1]
				array[0] = cos * a - sin * b
				array[1] = sin * a + cos * b
			}
		}
		return this
	}

	fun clone(): Matrix {
		return Matrix(Array(data.size) { index ->
            data[index].clone()
        })
	}

	fun FloatArray.clone(): FloatArray = FloatArray(size){ this[it] }

	private fun transposedData(): Array<FloatArray> {
		return Array(data[0].size) { i ->
			FloatArray(data.size) { j ->
				data[j][i]
			}
		}
	}

	fun transpose(): Matrix {
		data = transposedData()
		return this
	}

	fun transposed(): Matrix {
		return Matrix(transposedData())
	}

	fun inverse(): Matrix {

		val n = data.size - 1

		val newMatrixData = Array(n + 1) { i -> FloatArray(n + 1) { j -> if (i == j) 1f else 0f } }
		for (row in 0..n) {

			// multiply row...
			val rowData = data[row]
			val rowData2 = newMatrixData[row]
			val element = rowData[row]
			if (element != 1f) {
				if (abs(element) < .001) {

					// suche eine Spalte, mit der wir uns reparieren können
					var absMax = abs(element)
					var bestI = row
					for (i in 0..n) {
						if (i != row && abs(data[i][row]) > absMax) {
							absMax = abs(data[i][row])
							bestI = i
						}
					}
					if (bestI == row) {
						throw RuntimeException("Matrix is not invertible!")
					}

					// addiere diese Zeile auf unsere drauf, bis unser Wert = 1 ist
					val bestRow = data[bestI]
					val bestRow2 = newMatrixData[bestI]
					val factor = (1 - element) / bestRow[row]
					/*for(i in 0 .. 3){// can be made a little more efficient because of the first its known to be 0
                        rowData[i] += factor * bestRow[i]
                        rowData2[i] += factor * bestRow2[i]
                    }*/
					for (i in row + 1..n) {// can be made a little more efficient because of the first its known to be 0
						rowData[i] += factor * bestRow[i]
					}
					for (i in 0..n) {
						rowData2[i] += factor * bestRow2[i]
					}

					// is allowed
					rowData[row] = 1f

				} else {
					// multiply the line
					val factor = 1 / element
					rowData[row] = 1f
					for (i in row + 1..n) {
						rowData[i] *= factor
					}
					for (i in 0..n) {
						rowData2[i] *= factor
					}
				}
			}

			// add the other rows...
			for (row2 in 0..n) {
				if (row2 != row) {
					val other = data[row2]
					val other2 = newMatrixData[row2]
					val factor = -other[row]
					// add self onto other
					other[row] = 0f
					for (i in row + 1..n) {
						other[i] += factor * rowData[i]
					}
					for (i in 0..n) {
						other2[i] += factor * rowData2[i]
					}
				}
			}
		}

		this.data = newMatrixData
		return this
	}

	fun writeUnit(): Matrix {
		for (i in 0 until data.size) {
			val row = data[i]
			for (j in 0 until row.size) {
				row[j] = if (i == j) 1f else 0f
			}
		}
		return this
	}

	fun preConcat(matrix: Matrix){
		data = (matrix * this).data
	}

	override fun toString(): String {
		return data.joinToString("],\n\t[", prefix = "[\n\t[", postfix = "]\n]") { x -> x.joinToString(", ") }
	}

	companion object {

		fun frameHappened() {}

		private fun clean(array: Array<FloatArray>, unitNotZero: Boolean) {
			val s = array.size
			val z = array[0].size
			if (unitNotZero) {
				for (i in 0 until s) {
					for (j in 0 until z) {
						array[i][j] == if (i == j) 1f else 0f
					}
				}
			} else {
				for (i in 0 until s) {
					for (j in 0 until z) {
						array[i][j] = 0f
					}
				}
			}
		}

		fun I(size: Int = 4): Matrix {
			return Matrix(size, true)
		}

		fun perspectiveMatrix(wfh: Float, fov: Float, near: Float, far: Float, uuid: Int): Matrix {
			val fDif = 1 / (far - near)
			val fTan = 1 / tan(fov / 2)
			return Matrix(
                arrayOf(
                    floatArrayOf(fTan / wfh, 0f, 0f, 0f),
                    floatArrayOf(0f, fTan, 0f, 0f),
                    floatArrayOf(0f, 0f, -(far + near) * fDif, -2 * far * near * fDif),
                    floatArrayOf(0f, 0f, -1f, 0f)
                )
            )
		}

		/*fun orthographicMatrix(sizeX: Float, sizeY: Float, sizeZ: Float): Matrix {
            return Matrix(arrayOf(
                FloatArrayOf(1/sizeX, 0f, 0f, 0f),
                FloatArrayOf(0f, 1/sizeY, 0f, 0f),
                FloatArrayOf(0f, 0f, 1/sizeZ, 0f),
                FloatArrayOf(0f, 0f, 0f, 1f)
            ))
        }*/

	}

}