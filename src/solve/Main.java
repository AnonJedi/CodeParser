package solve;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String []args)
    {
        String []info = ReadInfo();

        IO.CreateFile();
        Process(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
    }

    private static String[] ReadInfo()  //метод-опросник
    {
        String []info = new String[3];
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter path to file: ");
            info[0] = reader.readLine();

            System.out.println("Enter size of retreat: ");
            while (true)
            {
                try
                {
                    info[1] = reader.readLine();
                    Integer.parseInt(info[1]);
                    break;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Bad size! Try again: ");
                }
            }

            System.out.println("Choice style:\r\n1) if(){\r\n   }\r\n2) if()\r\n   {}");
            while (true)
            {
                try
                {
                    info[2] = reader.readLine();
                    Integer.parseInt(info[2]);
                    break;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Bad type! Try again: ");
                }
            }
            reader.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return info;
    }

    private static void Process(String fileName, int spaceSize, int style)    //логика программы
    {
        PseudoTuple result = new PseudoTuple(new ArrayList<String>(), 0);
        result.list.add(" ");

        try
        {
            BufferedReader fileReader = new BufferedReader(new FileReader(new File(fileName)));

            while (true)
            {
                IO io = new IO();
                char c = io.Reader(fileReader); //считывает по одному символу за раз
                result = Parser.Parse(c, result, spaceSize, style); //вставляет этот символ на своё место

                if (result.list.size() > 1)
                {
                    IO.Writer(result.list);
                    result.list = new ArrayList<String>();
                    result.list.add(" ");
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (IndexOutOfBoundsException e){}   //условие выхода из цикла
    }
}
