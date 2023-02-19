cmake_minimum_required(VERSION 3.14)

project(mtc VERSION 0.1 LANGUAGES CXX)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(Qt6 REQUIRED COMPONENTS Core Gui Scxml Widgets)

set(PROJECT_SOURCES
        main.cpp
        mtcpanel.cpp
)

qt_add_executable(mtc
    mtcpanel.cpp mtcpanel.h main.cpp
)

target_link_libraries(mtc PUBLIC
    Qt6::Core
    Qt6::Gui
    Qt6::Scxml
    Qt6::Widgets
)

set_source_files_properties("./tolling.scxml"
    PROPERTIES QT_RESOURCE_ALIAS "tolling.scxml"
)

qt6_add_resources(mtc "mtc"
    PREFIX
        "/"
    FILES
        "./tolling.scxml"
)


set_target_properties(mtc PROPERTIES
    MACOSX_BUNDLE TRUE
    WIN32_EXECUTABLE TRUE
)

install(TARGETS mtc
    BUNDLE DESTINATION .
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})

