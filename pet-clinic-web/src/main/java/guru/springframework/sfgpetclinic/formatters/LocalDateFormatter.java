package guru.springframework.sfgpetclinic.formatters;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public final class LocalDateFormatter
    implements Formatter<LocalDate>
{
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate parse(final String text, final Locale locale)
        throws ParseException
    {
        return LocalDate.from(formatter.parse(text));
    }

    @Override
    public String print(final LocalDate date, final Locale locale)
    {
        return date.format(formatter);
    }
}