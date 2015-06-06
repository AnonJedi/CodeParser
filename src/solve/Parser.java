package solve;

import java.util.ArrayList;

public class Parser
{
    private static int spaceCounter = 0;
    private static char[] memory = {' ', ' ', ' ', ' ', ' '};

    private static char[] MemoryShift(char[] memory, char symbol)
    {
        if (memory[0] != '#') memory[0] = memory[1];
        memory[1] = memory[2];
        memory[2] = memory[3];
        memory[3] = symbol;

        return memory;
    }

    private static String Parse(char symbol, int spaceSize, int style)
    {
        ArrayList<Character> specChar = new ArrayList<Character>(); //символы операций
        specChar.add('*');
        specChar.add('+');
        specChar.add('-');
        specChar.add('=');
        specChar.add('|');
        specChar.add('&');
        specChar.add('>');
        specChar.add('<');

        ArrayList<Character> endString = new ArrayList<Character>();    //символы конца строки
        endString.add(';');
        endString.add('{');
        endString.add('}');

        StringBuilder stringBuilder = new StringBuilder();

        if ((symbol == '\n') || (symbol == '\r')) return null;

        String space = "";

        if (symbol == '{') memory[0] = ' ';

        boolean checkSpace;
        if (endString.contains(memory[3]) && memory[0] != '#')
        {
            if (symbol == '}') spaceCounter--;
            for (int i = 0; i < spaceCounter; i++)
            {
                for (int j = 0; j < spaceSize; j++) space += " ";   //размер отступа
            }
            stringBuilder.append(space);
            checkSpace = true;
        }
        else checkSpace = false;

        if (checkSpace && (symbol == ' ')) return null;   //если код уже отформатирован, не вносить лишние отступы

        if ((memory[0] == '#') && (symbol == ';'))
        {
            stringBuilder.append(symbol + " ");
            MemoryShift(memory, symbol);
            return stringBuilder.toString();
        }   //конец определения условий for

        if (endString.contains(symbol)) //проверка на конец строки
        {
            if (symbol != '{' || style == 1)
            {
                stringBuilder.append(symbol + "\r\n");
                memory[0] = ' ';
                MemoryShift(memory, symbol);
                if (symbol == '{') spaceCounter++;

                return stringBuilder.toString();
            }
            else if (symbol == '{' && style == 2)
            {
                for (int i = 0; i < spaceCounter; i++)
                {
                    for (int j = 0; j < spaceSize; j++) space += " ";   //размер отступа
                }
                stringBuilder.append("\r\n" + space + symbol + "\r\n");
                spaceCounter++;
                MemoryShift(memory, symbol);

                return stringBuilder.toString();
            }
        }
        else if ((symbol == ' ') || (symbol == '('))    //определение операторов
        {
            if ((memory[3] == 'f') && (memory[2] == 'i') && ((memory[1] == ' ') || endString.contains(memory[1]))) //определение if
            {
                if (symbol == ' ') stringBuilder.append(symbol);
                else stringBuilder.append(" " + symbol);

                MemoryShift(memory, symbol);
                return stringBuilder.toString();
            }
            else if ((memory[3] == 'r') && (memory[2] == 'o') && (memory[1] == 'f') && ((memory[0] == ' ') || endString.contains(memory[0]))) //определение for
            {
                memory[0] = '#';

                if (symbol == ' ') stringBuilder.append(symbol);
                else stringBuilder.append(" " + symbol);

                MemoryShift(memory, symbol);
                return stringBuilder.toString();
            }
            else stringBuilder.append(symbol);

            MemoryShift(memory, symbol);
            return stringBuilder.toString();
        }
        else if (specChar.contains(symbol)) //проверка на символ операции
        {
            if (!specChar.contains(memory[3]) && memory[3] != ' ') stringBuilder.append(" " + symbol);
            else stringBuilder.append(symbol);

            MemoryShift(memory, symbol);
            return stringBuilder.toString();
        }
        else    //запись обычного символа
        {
            if (specChar.contains(memory[3]) && (symbol != ')')) stringBuilder.append(" " + symbol);
            else stringBuilder.append(symbol);

            MemoryShift(memory, symbol);
            return stringBuilder.toString();
        }

        return null;
    }

    public static void Process(Reader reader, Writer writer, int spaceSize, int style)    //логика программы
    {
        while (true)
        {
            try
            {
                char c = reader.getChar(); //считывает по одному символу за раз
                String s = Parser.Parse(c, spaceSize, style); //вставляет этот символ на своё место
                writer.Save(s);
            }
            catch (NullPointerException e) {}
            catch (IndexOutOfBoundsException e) //условие выхода из цикла
            {
                break;
            }
        }
    }
}
