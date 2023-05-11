import os
import typing

from PyQt5 import QtCore
from PyQt5.QtWidgets import QWidget, QPushButton, QHBoxLayout, QVBoxLayout, \
    QGridLayout, QMainWindow, QLineEdit, QFileDialog, QLabel, QProgressBar, QMessageBox

from tableWidget import tableWidget

class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle('收费站控制面板')
        self.setGeometry(0, 0, 960, 600)
        
        self.tableWidget = tableWidget(self)
        self.setCentralWidget(self.tableWidget)

        self.show()
