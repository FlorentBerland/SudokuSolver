package ui

import java.awt.{Color, Container, FlowLayout, GridLayout}

import javax.swing.border.LineBorder
import javax.swing.{JButton, JComponent, JLabel, JPanel}
import model.Square

class SquareDisplay(_square: Square) extends JComponent {

  init()

  private def init(): Unit = {
    this.setBorder(new LineBorder(Color.gray, 1))
    _square.value match {
      case Left(value) =>
        this.setLayout(new FlowLayout())
        this.add(new JLabel(value.toString))
        this.setBackground(Color.lightGray)
      case Right(candidates) =>
        this.setLayout(new GridLayout(3, 3))
        (1 to 9).foreach(value => {
          if(candidates.contains(value)) this.add(new JLabel(value.toString))
          else this.add(new JLabel(" "))
        })
      case _ =>
        this.setBackground(Color.lightGray)
    }

  }

}
