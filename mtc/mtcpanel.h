#ifndef MTCPANEL_H
#define MTCPANEL_H

#include <QScxmlStateMachine>
#include <QWidget>
#include <QGroupBox>
#include <QStackedLayout>
#include "qamqp/src/qamqpclient.h"

class MtcPanel : public QWidget
{
    Q_OBJECT
public:
    MtcPanel(QScxmlStateMachine *machine, QWidget *parent = nullptr);


signals:

private:
    QScxmlStateMachine *m_machine;

    QStackedLayout *stack;

    QGroupBox *approachedGroup;
    QGroupBox *recognizingGroup;
    QGroupBox *recognizedGroup;

    QGroupBox *currentGroup = NULL;

public slots:

    void intoApproached();
    void intoRecoginizing();
    void intoRecoginized();

private:
    QGroupBox *createApproachedGroup();
    QGroupBox *createRecognizingGroup();
    QGroupBox *createRecognizedGroup();

};

#endif // MTCPANEL_H
