#!/bin/bash

echo "Launching program..."
sleep 1
dossier=".data"

if [ -d "$dossier" ]; then
    echo ".data folder exists. Continuing..."
else
    echo ".data folder doesn't exist. Creating..."
    mkdir .data
fi
# Stocke le chemin dans CSV_PATH

java --module-path lib/openjfx-21.0.7_linux-x64_bin-sdk/javafx-sdk-21.0.7/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar IHM-App.jar

