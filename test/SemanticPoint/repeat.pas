program repeatUntilLoop;
var
   a: integer;

begin
   a := 10;
   repeat
      a := a + 1
   until a - 2; { a expressao não é boolean}
end.
