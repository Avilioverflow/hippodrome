import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Nested
    @DisplayName("Тесты конструктора класса")
    class HippodromeConstructorTests{
        @Test
        @DisplayName("Проверка исключения при null аргументе List")
        public void checkExceptionNullListArgument(){
            assertThrows(IllegalArgumentException.class,() -> new Hippodrome(null));
        }

        @Test
        @DisplayName("Проверка сообщения исключения при null аргументе List")
        public void checkExceptionMessageNullListArgument(){
            try {
                new Hippodrome(null);
                fail("Expected exception was not thrown");
            }catch (IllegalArgumentException e){
                assertEquals("Horses cannot be null.",e.getMessage());
            }
        }

        @Test
        @DisplayName("Проверка исключения при передачи пустого списка в качестве аргумента")
        public void checkExceptionEmptyListArgument(){
            assertThrows(IllegalArgumentException.class,() -> new Hippodrome(Collections.<Horse>emptyList()));
        }

        @Test
        @DisplayName("Проверка сообщения исключения при передачи пустого списка в качестве аргумента")
        public void checkExceptionMessageEmptyListArgument(){
            try {
                new Hippodrome(Collections.<Horse>emptyList());
                fail("Expected exception was not thrown");
            }catch (IllegalArgumentException e){
                assertEquals("Horses cannot be empty.",e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Проверка метода getHorses")
    public void checkGetHorses(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Horse horse = new Horse("Horse " + i, i);
            horses.add(horse);
        }

        assertEquals(horses,new Hippodrome(horses).getHorses());
    }

    @Test
    @DisplayName("Проверка метода move")
    public void checkMove(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            Horse horseMock = Mockito.mock(Horse.class);
            horses.add(horseMock);
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horseMock : horses){
            Mockito.verify(horseMock,Mockito.times(1)).move();
        }
    }

    @Test
    @DisplayName("Проверка метода getWinner")
    public void checkGetWinner(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 10 ; i++){
            horses.add(new Horse("Horse" + i,i));
        }
        Horse superHorse = new Horse("Super Horse",10,Double.MAX_VALUE);
        horses.add(superHorse);


        assertSame(superHorse,new Hippodrome(horses).getWinner());  //можно и assertEquals (необходимо переопределять equals..), но этого хватает - достаточно сравнить ссылки
    }




}