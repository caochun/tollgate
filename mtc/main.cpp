#include "mtcpanel.h"

#include <QApplication>
#include <QIODevice>
#include <QTextStream>

int main(int argc, char **argv)
{
    QApplication app(argc, argv);

    QScxmlStateMachine *machine = QScxmlStateMachine::fromFile(
                QStringLiteral(":tolling.scxml"));
    if (!machine->parseErrors().isEmpty()) {
        QTextStream errs(stderr, QIODevice::WriteOnly);
        const auto errors = machine->parseErrors();
        for (const QScxmlError &error : errors) {
            errs << error.toString();
        }

        return -1;
    }

    MtcPanel widget(machine);
    widget.show();
    machine->setParent(&widget);
    machine->start();

    return app.exec();
}
