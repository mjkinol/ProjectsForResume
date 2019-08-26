all: scrabble

scrabble: scrabble.cpp Player.o Board.o Move.o ConsolePrinter.o Dictionary.o Bag.o
	g++ -g -Wall --std=c++11 scrabble.cpp Player.o Board.o Move.o ConsolePrinter.o Dictionary.o Bag.o -o scrabble

Player.o: Player.h Player.cpp
	g++ -g -Wall --std=c++11 -c Player.cpp -o Player.o

Board.o: Board.h Board.cpp
	g++ -g -Wall --std=c++11 -c Board.cpp -o Board.o

Move.o: Move.h Move.cpp
	g++ -g -Wall --std=c++11 -c Move.cpp -o Move.o

ConsolePrinter.o: ConsolePrinter.h ConsolePrinter.cpp
	g++ -g -Wall --std=c++11 -c ConsolePrinter.cpp -o ConsolePrinter.o

Dictionary.o: Dictionary.h Dictionary.cpp
	g++ -g -Wall --std=c++11 -c Dictionary.cpp -o Dictionary.o

Bag.o: Bag.h Bag.cpp
	g++ -g -Wall --std=c++11 -c Bag.cpp -o Bag.o
