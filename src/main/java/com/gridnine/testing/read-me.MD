## Решение задачи фильтрации полетов

Согласно условиям задания необходимо проверить тестовый набор полетов на ряд критериев

исключить полеты, в которых общее время пребывания на земле

* Вылет до текущего момента времени
* Сегменты с датой прилёта раньше даты вылета
* Перелеты, где общее время, проведённое на земле, превышает два часа (время на земле — это интервал между прилётом
  одного сегмента и вылетом следующего за ним)

Для этого планируем написать методы, реализующие следующие функции:

* Проверка времени вылета
* Проверка времени прилета
* Вычисление общего времени, проведенного на земле

Указанные методы будут объединены в класс FlightService.java

Дополнительно, на наш взгляд, целесообразно реализовать методы для реализации следующих функций:
Проверка корректности сегмента (время вылета ранее времени прилета)
Проверке корректности полета (сегменты корректны и не "наезжают" друг на друга)