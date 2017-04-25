#!/bin/sh

set -ex
wget https://github.com/google/protobuf/archive/v3.2.0.tar.gz
tar -xzvf v3.2.0.tar.gz
cd protobuf-3.2.0
./autogen.sh
./configure --prefix=/usr
make
sudo make install
