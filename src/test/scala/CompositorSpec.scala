import org.scalatest.WordSpec

class CompositorSpec extends WordSpec {

  "A Compositor" when {
    "empty" should {
      "return the input value unmodified" in {
        assertResult(1) {Compositor[Int]()(1)}
      }
    }

    "non-empty" should {
      "return the value modified by the composed functions" in {
        assertResult(4){Compositor[Int](i => i+1, i => i+2)(1)}
      }
      "apply the functions in the right order" in {
        val add: Int => Int = i => i+1
        val mul: Int => Int = i => i*2
        assert(Compositor[Int](add, mul)(1) != Compositor[Int](mul, add)(1))
      }
    }
  }

}
