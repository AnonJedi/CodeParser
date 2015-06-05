package solve;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
    public static PseudoTuple Parse(char symbol, PseudoTuple result, int spaceSize, int style)
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

        List<String> buffer = result.list;
        int counter = result.spaceCounter;

        char []splitString = buffer.get(buffer.size()-1).toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        int maxIndex = splitString.length - 1;
        stringBuilder.append(buffer.get(buffer.size()-1));

        if ((symbol == '\n') || (symbol == '\r')) //если код уже отформатирован, не добавлять лишние переносы
        {
            symbol = ' ';
            return result;
        }

        String space = "";
        if (splitString.length == 1)
        {
            if (symbol == '}') counter--;

            for (int i = 0; i < counter; i++)
            {
                for (int j = 0; j < spaceSize; j++) space += " ";   //размер отступа
            }
            stringBuilder.append(space);
        }

        if ((splitString.length <= (counter * spaceSize)) && (symbol == ' '))   //если код уже отформатирован, не вносить лишние отступы
        {
            for (char c : splitString)
            {
                if (c == ' ') return result;
            }
        }

        Pattern pattern = Pattern.compile(".*#.*"); //определение условий for
        Matcher matcher = pattern.matcher(buffer.get(buffer.size()-1));
        if (matcher.find())
        {
            if (symbol == ';')
            {
                stringBuilder.append("# ");
                buffer.set(buffer.size()-1, stringBuilder.toString());
                result.list = buffer;
                return result;
            }
        }   //конец определения условий for

        if (endString.contains(symbol)) //проверка на конец строки, теперь нормализация здесь
        {
            if (symbol != '{' || style == 1)
            {
                stringBuilder.append(symbol);

                splitString = stringBuilder.toString().toCharArray();
                stringBuilder = new StringBuilder();

                for (int i = 1; i < splitString.length; i++)
                {
                    if (splitString[i] == '#')
                    {
                        stringBuilder.append(';');
                        continue;
                    }
                    else stringBuilder.append(splitString[i]);
                }
                buffer.set(buffer.size() - 1, stringBuilder.toString());
            }
            else if (symbol == '{' && style == 2)
            {
                splitString = stringBuilder.toString().toCharArray();
                stringBuilder = new StringBuilder();

                for (int i = 1; i < splitString.length; i++)
                {
                    if (splitString[i] == '#')
                    {
                        stringBuilder.append(';');
                        continue;
                    }
                    else stringBuilder.append(splitString[i]);
                }
                buffer.set(buffer.size() - 1, stringBuilder.toString());

                for (int i = 0; i < counter; i++)
                {
                    for (int j = 0; j < spaceSize; j++) space += " ";
                }
                buffer.add(space + String.valueOf(symbol));
            }

            buffer.add(" ");
            result.list = buffer;

            if (symbol == '{') result.spaceCounter++;
            else if (symbol == '}') result.spaceCounter--;

            return result;
        }
        else if (((symbol == ' ') || (symbol == '(')) && (maxIndex > 0))    //определение операторов
        {
            if ((splitString[maxIndex] == 'f') && (splitString[maxIndex-1] == 'i')) //определение if
            {
                if (maxIndex > 1)
                {
                    if (splitString[maxIndex-2] == ' ') stringBuilder.append(" " + symbol);
                    else stringBuilder.append(symbol);
                }
                else stringBuilder.append(" " + symbol);
            }
            else if (maxIndex > 1)
            {
                if ((splitString[maxIndex] == 'r') && (splitString[maxIndex-1] == 'o') && (splitString[maxIndex-2] == 'f')) //определение for
                {
                    if (maxIndex > 2)
                    {
                        if (splitString[maxIndex-3] == ' ')
                        {
                            splitString[0] = '#';
                            stringBuilder = new StringBuilder();
                            for (char c : splitString) stringBuilder.append(String.valueOf(c));
                            stringBuilder.append(" " + symbol);
                        }
                    }
                    else
                    {
                        splitString[0] = '#';
                        stringBuilder = new StringBuilder();
                        for (char c : splitString) stringBuilder.append(String.valueOf(c));
                        stringBuilder.append(" " + symbol);
                    }
                }
                else stringBuilder.append(symbol);
            }
        }
        else if (specChar.contains(symbol)) //проверка на символ операции
        {
            if (maxIndex > 0)
            {
                if ((splitString[maxIndex] == '+') && (symbol == '+') || (splitString[maxIndex] == '-') && (symbol == '-')) //++ и -- пишутся без отступов
                {
                    splitString[maxIndex-1] = symbol;
                    stringBuilder = new StringBuilder();

                    for (char c : splitString) stringBuilder.append(String.valueOf(c));
                    buffer.set(buffer.size()-1, stringBuilder.toString());
                    result.list = buffer;
                    return result;
                }
                else if (!specChar.contains(splitString[maxIndex])) stringBuilder.append(" " + symbol);
                else stringBuilder.append(symbol);
            }
            else stringBuilder.append(symbol);
        }
        else    //запись обычного символа
        {
            if (specChar.contains(splitString[maxIndex]) && (symbol != ')')) stringBuilder.append(" " + symbol);
            else stringBuilder.append(symbol);
        }
        buffer.set(buffer.size()-1, stringBuilder.toString());
        result.list = buffer;

        return result;
    }
}
