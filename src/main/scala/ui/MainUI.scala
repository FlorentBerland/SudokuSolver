package ui

import java.awt.Dimension
import java.awt.event.{ComponentEvent, ComponentListener, WindowAdapter, WindowListener}

import javax.swing.{JFrame, WindowConstants}
import model.Square

class MainUI(grid: Set[Square]) extends JFrame {

  private val self = this
  private val gridDisplay = new GridDisplay(grid)

  init()

  private def init(): Unit = {
    this.setVisible(true)
    this.setPreferredSize(new Dimension(500, 500))
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    this.add(gridDisplay)
    this.addComponentListener(new ComponentListener {
      override def componentResized(componentEvent: ComponentEvent): Unit = {
        if(componentEvent.getComponent == self){
          gridDisplay.setPreferredSize(new Dimension(self.getSize.width, self.getSize.height - 50))
          gridDisplay.paint(gridDisplay.getGraphics)
        }
      }

      override def componentMoved(componentEvent: ComponentEvent): Unit = {}

      override def componentShown(componentEvent: ComponentEvent): Unit = {}

      override def componentHidden(componentEvent: ComponentEvent): Unit = {}
    })
    this.pack()
  }



}
