package com.twitter.scalding.mathematics

import com.twitter.scalding._
import cascading.pipe.joiner._
import org.specs._
import com.twitter.algebird.{Ring,Group}

class Matrix2Sum(args: Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._
  
  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, Double)](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)

  val p2 = Tsv("mat2", ('x2, 'y2, 'v2)).read
  val tp2 = p2.toTypedPipe[(Int, Int, Double)](('x2, 'y2, 'v2))
  val mat2 = MatrixLiteral(tp2, NoClue)
  
  val sum = mat1 + mat2
  sum.toTypedPipe.write(TypedTsv[(Int,Int,Double)]("sum"))
}

class Matrix2Sum3(args: Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._
  
  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, (Double, Double, Double))](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)

  val sum = mat1 + mat1
  sum.toTypedPipe.write(TypedTsv[(Int,Int,(Double, Double, Double))]("sum"))
}

class Matrix2SumChain(args: Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._
  
  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, Double)](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)

  val p2 = Tsv("mat2", ('x2, 'y2, 'v2)).read
  val tp2 = p2.toTypedPipe[(Int, Int, Double)](('x2, 'y2, 'v2))
  val mat2 = MatrixLiteral(tp2, NoClue)

  val p3 = Tsv("mat3", ('x3, 'y3, 'v3)).read
  val tp3 = p3.toTypedPipe[(Int, Int, Double)](('x3, 'y3, 'v3))
  val mat3 = MatrixLiteral(tp3, NoClue)  
  
  val sum = mat1 + mat2 + mat3
  sum.toTypedPipe.write(TypedTsv[(Int,Int,Double)]("sum"))
}

class Matrix2RowRowHad(args : Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._
  
  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, Double)](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)

  val row1 = mat1.getRow(1)
  val rowSum = row1 #*# row1
  rowSum.toTypedPipe.map { case (x, idx, v) => (idx, v) }.write(TypedTsv[(Int,Double)]("rowRowHad"))
}

class Matrix2ZeroHad(args : Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._
  
  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, Double)](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)

  val p2: Pipe = Tsv("mat2", ('x2, 'y2, 'v2)).read
  val tp2 = p2.toTypedPipe[(Int, Int, Double)](('x2, 'y2, 'v2))
  val mat2 = MatrixLiteral(tp2, NoClue)  
  
  val rowSum = mat1 #*# mat2
  rowSum.toTypedPipe.write(TypedTsv[(Int,Int,Double)]("zeroHad"))
}

class Matrix2HadSum(args: Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._
  
  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, Double)](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)

  val p2 = Tsv("mat2", ('x2, 'y2, 'v2)).read
  val tp2 = p2.toTypedPipe[(Int, Int, Double)](('x2, 'y2, 'v2))
  val mat2 = MatrixLiteral(tp2, NoClue)

  val p3 = Tsv("mat3", ('x3, 'y3, 'v3)).read
  val tp3 = p3.toTypedPipe[(Int, Int, Double)](('x3, 'y3, 'v3))
  val mat3 = MatrixLiteral(tp3, NoClue)  
  
  val sum = mat1 #*# (mat2 + mat3)
  sum.toTypedPipe.write(TypedTsv[(Int,Int,Double)]("hadSum"))
}

class Matrix2Prod(args: Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._

  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, Double)](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)

  val gram = mat1 * mat1.transpose
  gram.toTypedPipe.write(TypedTsv[(Int,Int,Double)]("product"))
}

class Matrix2ProdSum(args: Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._

  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, Double)](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)
  
  val p2 = Tsv("mat2", ('x2, 'y2, 'v2)).read
  val tp2 = p2.toTypedPipe[(Int, Int, Double)](('x2, 'y2, 'v2))
  val mat2 = MatrixLiteral(tp2, NoClue)

  val gram = (mat1 * mat1.transpose) + mat2
  gram.toTypedPipe.write(TypedTsv[(Int,Int,Double)]("product-sum"))
}

class Matrix2PropJob(args: Args) extends Job(args) {
  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._
  
  val tsv1 = TypedTsv[(Int,Int,Int)]("graph")
  val p1 = tsv1.toPipe(('x1, 'y1, 'v1))
  val tp1 = p1.toTypedPipe[(Int, Int, Int)](('x1, 'y1, 'v1))
  val mat = MatrixLiteral(tp1, NoClue)  

  val tsv2 = TypedTsv[(Int,Double)]("col")
  val col = MatrixLiteral(TypedPipe.from(tsv2).map { case (idx, v) => (idx, (), v) }, NoClue)

  val tsv3 = TypedTsv[(Int,Double)]("row")
  val row = MatrixLiteral(TypedPipe.from(tsv3).map { case (idx, v) => ((), idx, v) }, NoClue)
  
  mat.binarizeAs[Boolean].propagate(col).toTypedPipe.map { case (idx, x, v) => (idx, v) }.write(TypedTsv[(Int,Double)]("prop-col"))
  row.propagateRow(mat.binarizeAs[Boolean]).toTypedPipe.map { case (x, idx, v) => (idx, v) }.write(TypedTsv[(Int,Double)]("prop-row"))  
}

class Matrix2Cosine(args : Args) extends Job(args) {

  import Matrix2._
  import cascading.pipe.Pipe
  import cascading.tuple.Fields
  import com.twitter.scalding.TDsl._

  val p1: Pipe = Tsv("mat1", ('x1, 'y1, 'v1)).read
  val tp1 = p1.toTypedPipe[(Int, Int, Double)](('x1, 'y1, 'v1))
  val mat1 = MatrixLiteral(tp1, NoClue)

  val matL2Norm = mat1.rowL2Normalize
  val cosine = matL2Norm * matL2Norm.transpose
  cosine.toTypedPipe.write(TypedTsv[(Int,Int,Double)]("cosine"))
}

class Matrix2Test extends Specification {
  noDetailedDiffs() // For scala 2.9
  import Dsl._

  def toSparseMat[Row, Col, V](iter: Iterable[(Row, Col, V)]): Map[(Row, Col), V] = {
    iter.map { it => ((it._1, it._2), it._3) }.toMap
  }
  def oneDtoSparseMat[Idx, V](iter: Iterable[(Idx, V)]): Map[(Idx, Idx), V] = {
    iter.map { it => ((it._1, it._1), it._2) }.toMap
  }

  "A MatrixSum job" should {
    TUtil.printStack {
      JobTest("com.twitter.scalding.mathematics.Matrix2Sum")
        .source(Tsv("mat1", ('x1, 'y1, 'v1)), List((1, 1, 1.0), (2, 2, 3.0), (1, 2, 4.0)))
        .source(Tsv("mat2", ('x2, 'y2, 'v2)), List((1, 3, 3.0), (2, 1, 8.0), (1, 2, 4.0)))
        .sink[(Int, Int, Double)](TypedTsv[(Int,Int,Double)]("sum")) { ob =>
          "correctly compute sums" in {
            val pMap = toSparseMat(ob)
            pMap must be_==(Map((1, 1) -> 1.0, (1, 2) -> 8.0, (1, 3) -> 3.0, (2, 1) -> 8.0, (2, 2) -> 3.0))
          }
        }
        .run
        .finish
    }
  }

  "A Matrix2Sum3 job, where the Matrix contains tuples as values," should {
    TUtil.printStack {
      JobTest("com.twitter.scalding.mathematics.Matrix2Sum3")
        .source(Tsv("mat1", ('x1, 'y1, 'v1)), List((1,1,(1.0, 3.0, 5.0)),(2,2,(3.0, 2.0, 1.0)),(1,2,(4.0, 5.0, 2.0))))
        .sink[(Int, Int, (Double, Double, Double))](TypedTsv[(Int,Int,(Double, Double, Double))]("sum")) { ob =>
          "correctly compute sums" in {
            val pMap = toSparseMat(ob)
            pMap must be_==(Map((1,1)->(2.0, 6.0, 10.0), (2,2)->(6.0, 4.0, 2.0), (1,2)->(8.0, 10.0, 4.0)))
          }
        }
        .run
        .finish
    }
  }

  "A Matrix2SumChain job" should {
    TUtil.printStack {
      JobTest("com.twitter.scalding.mathematics.Matrix2SumChain")
        .source(Tsv("mat1", ('x1, 'y1, 'v1)), List((1, 1, 1.0), (2, 2, 3.0), (1, 2, 4.0)))
        .source(Tsv("mat2", ('x2, 'y2, 'v2)), List((1, 3, 3.0), (2, 1, 8.0), (1, 2, 4.0)))
        .source(Tsv("mat3", ('x3, 'y3, 'v3)), List((1, 3, 4.0), (2, 1, 1.0), (1, 2, 4.0)))
        .sink[(Int, Int, Double)](TypedTsv[(Int,Int,Double)]("sum")) { ob =>
          "correctly compute sums" in {
            val pMap = toSparseMat(ob)
            pMap must be_==(Map((1, 1) -> 1.0, (1, 2) -> 12.0, (1, 3) -> 7.0, (2, 1) -> 9.0, (2, 2) -> 3.0))
          }
        }
        .run
        .finish
    }
  }  

  "A Matrix2HadSum job" should {
    TUtil.printStack {
      JobTest("com.twitter.scalding.mathematics.Matrix2HadSum")
        .source(Tsv("mat1", ('x1, 'y1, 'v1)), List((1, 3, 1.0), (2, 2, 3.0)))
        .source(Tsv("mat2", ('x2, 'y2, 'v2)), List((1, 3, 3.0), (2, 1, 8.0), (1, 2, 4.0)))
        .source(Tsv("mat3", ('x3, 'y3, 'v3)), List((1, 3, 4.0), (2, 1, 1.0), (1, 2, 4.0)))
        .sink[(Int, Int, Double)](TypedTsv[(Int,Int,Double)]("hadSum")) { ob =>
          "correctly compute a combination of a Hadamard product and a sum" in {
            val pMap = toSparseMat(ob)
            pMap must be_==(Map((1, 3) -> 7.0))
          }
        }
        .run
        .finish
    }
  }    
  
  "A Matrix2 RowRowHad job" should {
    TUtil.printStack {
    JobTest("com.twitter.scalding.mathematics.Matrix2RowRowHad")
      .source(Tsv("mat1",('x1,'y1,'v1)), List((1,1,1.0),(2,2,3.0),(1,2,4.0)))
      .sink[(Int,Double)](TypedTsv[(Int,Double)]("rowRowHad")) { ob =>
        "correctly compute a Hadamard product of row vectors" in {
          val pMap = oneDtoSparseMat(ob)
          pMap must be_==( Map((1,1)->1.0, (2,2)->16.0) )
        }
      }
      .run
      .finish
    }
  }

  "A Matrix2 ZeroHad job" should {
    TUtil.printStack {
    JobTest("com.twitter.scalding.mathematics.Matrix2ZeroHad")
      .source(Tsv("mat1",('x1,'y1,'v1)), List((1,1,1.0),(2,2,3.0),(1,2,4.0)))
      .source(Tsv("mat2",('x2,'y2,'v2)), List())
      .sink[(Int,Int,Double)](TypedTsv[(Int,Int,Double)]("zeroHad")) { ob =>
        "correctly compute a Hadamard product with a zero matrix" in {
          val pMap = toSparseMat(ob)
          pMap must be_==( Map( ))
        }
      }
      .run
      .finish
    }
  }
  
  
  "A Matrix2Prod job" should {
    TUtil.printStack {
      JobTest("com.twitter.scalding.mathematics.Matrix2Prod")
        .source(Tsv("mat1", ('x1, 'y1, 'v1)), List((1, 1, 1.0), (2, 2, 3.0), (1, 2, 4.0)))
        .sink[(Int, Int, Double)](TypedTsv[(Int,Int,Double)]("product")) { ob =>
          "correctly compute products" in {
            val pMap = toSparseMat(ob)
            pMap must be_==(Map((1, 1) -> 17.0, (1, 2) -> 12.0, (2, 1) -> 12.0, (2, 2) -> 9.0))
          }
        }
        .run
        .finish
    }
  }

  "A Matrix2Prod job" should {
    TUtil.printStack {
      JobTest("com.twitter.scalding.mathematics.Matrix2ProdSum")
        .source(Tsv("mat1", ('x1, 'y1, 'v1)), List((1, 1, 1.0), (2, 2, 3.0), (1, 2, 4.0)))
        .source(Tsv("mat2", ('x2, 'y2, 'v2)), List((1, 1, 1.0), (1, 2, 1.0), (2, 1, 1.0), (2, 2, 1.0)))
        .sink[(Int, Int, Double)](TypedTsv[(Int,Int,Double)]("product-sum")) { ob =>
          "correctly compute products" in {
            val pMap = toSparseMat(ob)
            pMap must be_==(Map((1, 1) -> 18.0, (1, 2) -> 13.0, (2, 1) -> 13.0, (2, 2) -> 10.0))
          }
        }
        .run
        .finish
    }
  }
  
  "A Matrix2 Propagation job" should {
    TUtil.printStack {
    JobTest(new Matrix2PropJob(_))
       /* Sparse representation of the input matrix:
        * [[0 1 1],
        *  [0 0 1],
        *  [1 0 0]] = List((0,1,1), (0,2,1), (1,2,1), (2,0,1))
        *  
        *  Sparse representation of the input vector:
        * [1.0 2.0 4.0] = List((0,1.0), (1,2.0), (2,4.0))
        */
      .source(TypedTsv[(Int,Int,Int)]("graph"), List((0,1,1), (0,2,1), (1,2,1), (2,0,1)))
      .source(TypedTsv[(Int,Double)]("row"), List((0,1.0), (1,2.0), (2,4.0)))      
      .source(TypedTsv[(Int,Double)]("col"), List((0,1.0), (1,2.0), (2,4.0)))
      .sink[(Int, Double)](TypedTsv[(Int,Double)]("prop-col")) { ob =>
        "correctly propagate columns" in {
          ob.toMap must be_==(Map(0 -> 6.0, 1 -> 4.0, 2 -> 1.0))
        }
      }
      .sink[(Int,Double)](TypedTsv[(Int,Double)]("prop-row")) { ob =>
        "correctly propagate rows" in {
          ob.toMap must be_==(Map(0 -> 4.0, 1 -> 1.0, 2 -> 3.0))
        }
      }
      .run
      .finish
    }
  }  

  "A Matrix2 Cosine job" should {
    TUtil.printStack {
    JobTest("com.twitter.scalding.mathematics.Matrix2Cosine")
      .source(Tsv("mat1",('x1,'y1,'v1)), List((1,1,1.0),(2,2,3.0),(1,2,4.0)))
      .sink[(Int, Int, Double)](TypedTsv[(Int,Int,Double)]("cosine")) { ob =>
        "correctly compute cosine similarity" in {
          val pMap = toSparseMat(ob)
          pMap must be_==( Map((1,1)->1.0, (1,2)->0.9701425001453319, (2,1)->0.9701425001453319, (2,2)->1.0 ))
        }
      }
      .run
      .finish
    }
  }  
  
}
