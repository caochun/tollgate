#ifndef MTCPANEL_H
#define MTCPANEL_H

#include <QScxmlStateMachine>
#include <QWidget>

class MtcPanel : public QWidget
{
    Q_OBJECT
public:
    MtcPanel(QScxmlStateMachine *machine, QWidget *parent = nullptr);


signals:

private:
    QScxmlStateMachine *m_machine;

};

#endif // MTCPANEL_H
