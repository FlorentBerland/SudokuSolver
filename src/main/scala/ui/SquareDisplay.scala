package ui

import java.awt._

import javax.swing.border.LineBorder
import javax.swing.{JButton, JComponent, JLabel, JPanel}
import model.Square

class SquareDisplay(square: Square, allValues: Set[Any]) extends JComponent {

  init()

  private def init(): Unit = {
    this.setBorder(new LineBorder(Color.gray, 1))
    square.value match {
      case Left(value) =>
        this.setLayout(new FlowLayout())
        this.add(new JLabel(value.toString){
          setFont(new Font("Arial", Font.BOLD, 30))
        })
      case Right(candidates) =>
        val width = Math.sqrt(allValues.size).toInt
        val height = allValues.size / width
        this.setLayout(new GridLayout(width, height))
        allValues.toArray.sortBy(_.asInstanceOf[Int]).foreach(value => {
          if(candidates.contains(value)) this.add(new JLabel(value.toString))
          else this.add(new JLabel(" "))
        })
      case _ =>
    }

  }

}
