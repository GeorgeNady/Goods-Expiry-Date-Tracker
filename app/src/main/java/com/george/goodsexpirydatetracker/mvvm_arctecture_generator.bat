:: created by George Nady
:: 1/9/2021

ECHO "creating adapter directory..."
mkdir adapters

ECHO "creating base directory..."
mkdir base
cd base
ECHO "creating fragments directory inside base directory..."
mkdir fragments
cd ..

ECHO "creating database directory..."
mkdir db

ECHO "creating dependency injection directory..."
mkdir di

ECHO "creating models directory..."
mkdir models

ECHO "creating repositories directory..."
mkdir repositories

ECHO "creating ui directory..."
mkdir ui
cd ui
mkdir main
cd main
mkdir fragments
cd ..
cd ..

ECHO "creating utiles directory..."
mkdir utiles