cmake_minimum_required(VERSION 3.14)

project(mtc VERSION 0.1 LANGUAGES CXX)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(CMAKE_PREFIX_PATH /usr/local/Cellar/qt@5/5.15.8_2)

find_package(Qt5 REQUIRED COMPONENTS Core Gui Scxml Widgets)

set(PROJECT_SOURCES
        main.cpp
        mtcpanel.cpp
)

add_executable(mtc
    mtcpanel.cpp mtcpanel.h main.cpp
)

target_link_libraries(mtc PUBLIC
    Qt5::Core
    Qt5::Gui
    Qt5::Scxml
    Qt5::Widgets
)

set_source_files_properties("./tolling.scxml"
    PROPERTIES QT_RESOURCE_ALIAS "tolling.scxml"
)

#qt6_add_resources(mtc "mtc"
#    PREFIX
#        "/"
#    FILES
#        "./tolling.scxml"
#)


set_target_properties(mtc PROPERTIES
    MACOSX_BUNDLE TRUE
    WIN32_EXECUTABLE TRUE
)

install(TARGETS mtc
    BUNDLE DESTINATION .
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

