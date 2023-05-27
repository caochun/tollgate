import os
import typing

from PyQt5 import QtCore
from PyQt5.QtWidgets import QWidget, QPushButton, QHBoxLayout, QVBoxLayout, \
    QGridLayout, QTabWidget, QLineEdit, QFileDialog, QLabel, QProgressBar, QMessageBox
from PyQt5.QtGui import QIcon
from PyQt5.QtCore import pyqtSlot

from tab4 import tab4

class tableWidget(QWidget):
    def __init__(self, parent):
        super(QWidget, self).__init__(parent)
        self.layout = QVBoxLayout(self)
        self.tabs = QTabWidget()
        self.tabs.resize(parent.width(), parent.height())
        
        self.tab1 = QWidget()
        self.tab2 = QWidget()
        self.tab3 = QWidget()
        self.tab4 = QWidget()

        # Create tab 1
        self.tab1.layout = QVBoxLayout(self)
        self.confirmButton = QPushButton('确认车牌信息')
        self.confirmButton.clicked.connect(self.on_click)
        self.tab1.layout.addWidget(self.confirmButton)
        self.tab1.setLayout(self.tab1.layout)

        # Create tab 4
        self.tab4 = tab4(self)
        
        self.tabs.addTab(self.tab1, '车辆信息')
        self.tabs.addTab(self.tab2, '读卡信息')
        self.tabs.addTab(self.tab3, '收费处理')
        self.tabs.addTab(self.tab4, '抬杆管理')
        
        # Add tabs to widget
        self.layout.addWidget(self.tabs)
        self.setLayout(self.layout)
        
    def on_click(self):
        print('Confirmed!')
