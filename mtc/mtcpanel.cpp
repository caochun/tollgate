#include "mtcpanel.h"
#include <iostream>

#include <QPushButton>
#include <QVBoxLayout>

using namespace std;

MtcPanel::MtcPanel(QScxmlStateMachine *machine, QWidget *parent)
    : QWidget(parent), m_machine(machine) {


    stack = new QStackedLayout;

    setLayout(stack);

    setWindowTitle(tr("Group Boxes"));
    resize(480, 320);


    machine->connectToState(QStringLiteral("approached"), this,
                            &MtcPanel::intoApproached);

    machine->connectToState(QStringLiteral("recognizing"), this,
                            &MtcPanel::intoRecoginizing);

    machine->connectToState(QStringLiteral("recognized"), this,
                            &MtcPanel::intoRecoginized);
}

void MtcPanel::intoApproached() {

    approachedGroup = createApproachedGroup();

    cout<<"approached"<<endl;



    stack->removeWidget(stack->widget(0));

    stack->addWidget(approachedGroup);



}

void MtcPanel::intoRecoginizing() {

    recognizingGroup = createRecognizingGroup();


    stack->removeWidget(stack->widget(0));

    stack->addWidget(recognizingGroup);



}

void MtcPanel::intoRecoginized() {

    recognizedGroup = createRecognizedGroup();


    stack->removeWidget(stack->widget(0));

    stack->addWidget(recognizedGroup);



}

QGroupBox *MtcPanel::createApproachedGroup() {
    QGroupBox *groupBox = new QGroupBox(tr("&Approached"));

    QPushButton *pushButton = new QPushButton(tr("&To Recoginzing State"));

    QVBoxLayout *vbox = new QVBoxLayout;
    vbox->addWidget(pushButton);
    vbox->addStretch(1);
    groupBox->setLayout(vbox);

    connect(pushButton, &QPushButton::clicked, this,
            [this]() { m_machine->submitEvent("mtc_approached"); });

    return groupBox;
}

QGroupBox *MtcPanel::createRecognizingGroup() {
    QGroupBox *groupBox = new QGroupBox(tr("&Recognizing"));

    QPushButton *pushButton = new QPushButton(tr("&To Recognized State"));

    QVBoxLayout *vbox = new QVBoxLayout;
    vbox->addWidget(pushButton);
    vbox->addStretch(1);
    groupBox->setLayout(vbox);

    connect(pushButton, &QPushButton::clicked, this,
            [this]() { m_machine->submitEvent("recognition_successes"); });


    return groupBox;
}

QGroupBox *MtcPanel::createRecognizedGroup() {
    QGroupBox *groupBox = new QGroupBox(tr("&Recognized"));

    QPushButton *pushButton = new QPushButton(tr("&To Next State"));

    QVBoxLayout *vbox = new QVBoxLayout;
    vbox->addWidget(pushButton);
    vbox->addStretch(1);
    groupBox->setLayout(vbox);

    return groupBox;
}
