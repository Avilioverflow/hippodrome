import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Horse Tests")
@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Nested
    @DisplayName("Тесты конструктора класса")
    class ConstructorTests{
        @Test
        @DisplayName("Проверка на исключение при аргументе Name = null ")
        public void checkExceptionNullNameArg(){
            assertThrows(IllegalArgumentException.class,() -> new Horse(null,3));
        }

        @Test
        @DisplayName("Проверка сообщения исключения при аргументе Name = null ")
        public void checkExceptionMessageNullNameArg(){
            try {
                new Horse(null,3);
                fail("Expected exception was not thrown");
            }catch (IllegalArgumentException e){
                assertEquals("Name cannot be null.",e.getMessage());
            }
        }

        @ParameterizedTest
        @DisplayName("Проверка исключения при аргументе Name - пустом, содерждащем одни пробелы и.т.д ")
        @ValueSource(strings = {"","     "," ","\t","\n"})
        public void checkExceptionEmptyNameArg(String str){
            assertThrows(IllegalArgumentException.class,() -> new Horse(str,3));
        }

        @ParameterizedTest
        @DisplayName("Проверка сообщения исключения при  аргументе Name - пустом, содерждащем одни пробелы и.т.д ")
        @ValueSource(strings = {"","     "," ","\t","\n"})
        public void checkExceptionMessageEmptyNameArg(String str){
            try {
                new Horse(str,3);
                fail("Expected exception was not thrown");
            }catch (IllegalArgumentException e){
                assertEquals("Name cannot be blank.",e.getMessage());
            }
        }

        @ParameterizedTest
        @DisplayName("Проверка исключения при аргументе speed - отрицательное значение")
        @ValueSource(doubles = {-1, -2.5, Double.NEGATIVE_INFINITY})
        public void checkExceptionNegativeSpeedArg(Double number){
            assertThrows(IllegalArgumentException.class,() -> new Horse("Buzefal",number));
        }

        @ParameterizedTest
        @DisplayName("Проверка сообщения исключения при аргументе speed - отрицательное значение")
        @ValueSource(doubles = {-1, -2.5, Double.NEGATIVE_INFINITY})
        public void checkExceptionMessageNegativeSpeedArg(Double number){
            try {
                new Horse("Buzefal",number);
                fail("Expected exception was not thrown");
            }catch (IllegalArgumentException e){
                assertEquals("Speed cannot be negative.",e.getMessage());
            }
        }

        @ParameterizedTest
        @DisplayName("Проверка исключения при аргументе distance - отрицательное значение")
        @ValueSource(doubles = {-1, -2.5, Double.NEGATIVE_INFINITY})
        public void checkExceptionNegativeDistanceArg(Double number){
            assertThrows(IllegalArgumentException.class,() -> new Horse("Buzefal",3,number));
        }

        @ParameterizedTest
        @DisplayName("Проверка сообщения исключения при аргументе distance - отрицательное значение")
        @ValueSource(doubles = {-1, -2.5, Double.NEGATIVE_INFINITY})
        public void checkExceptionMessageNegativeDistanceArg(Double number){
            try {
                new Horse("Buzefal",3,number);
                fail("Expected exception was not thrown");
            }catch (IllegalArgumentException e){
                assertEquals("Distance cannot be negative.",e.getMessage());
            }
        }
    }

    @ParameterizedTest
    @DisplayName("Тест метода getName")
    @ValueSource(strings = {"Buzefal","Пегас","FT-150"})
    public void checkGetName(String str){
        assertEquals(str,new Horse(str,3).getName());
    }

    @ParameterizedTest
    @DisplayName("Тест метода getSpeed")
    @ValueSource(doubles = {0.1,1,1500,Double.MAX_VALUE})
    public void checkGetSpeed(Double num){
        assertEquals(num,new Horse("TestHorse",num).getSpeed());
    }

    @ParameterizedTest
    @DisplayName("Тест метода getDistance")
    @ValueSource(doubles = {0.1,1,1500,Double.MAX_VALUE})
    public void checkGetDistance(Double num){
        assertEquals(num,new Horse("TestHorse",3,num).getDistance());
    }

    @Test
    @DisplayName("Тест метода getDistance при конструкторе с двумя параметрами")
    public void checkGetDistanceTwoParameters(){
        assertEquals(0,new Horse("TestHorse",3).getDistance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5,10,15,7.3,10000})
    @DisplayName("Тест метода move с использованием мокирования static метода getRandomDouble")
    public void checkMove(Double testDouble){
        try(MockedStatic<Horse>horseMockedStatic = Mockito.mockStatic(Horse.class)){
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2,0.9)).thenReturn(testDouble);

            Horse horse = new Horse("TestHorse",3);
            double expected = testDouble * horse.getSpeed() + horse.getDistance();

            horse.move();
            double result = horse.getDistance();

            assertAll("Проверка логики метода move и статического метода getRandomDouble",
                    () -> assertEquals(expected,result,"Логика метода move нарушена"),
                    () -> horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2,0.9),Mockito.times(1))
                    );
        }
    }
}