package solve;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

public class Reader implements Input //класс чтения/записи
{
    public BufferedReader stream;

    public Reader(BufferedReader stream)
    {
        this.stream = stream;
    }

    @Override
    public char getChar()
    {
        try
        {
            char []buffer = new char[1];
            buffer[0] = '$';
            stream.read(buffer);

            if (buffer[0] == '$')   //проверка конца файла
            {
                stream.close();
                throw new IndexOutOfBoundsException();
            }
            return buffer[0];
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
