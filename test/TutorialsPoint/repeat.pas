program repeatUntilLoop;
var
   a: integer;

begin
   a := 10;
   (* repeat until loop execution *)
   repeat
      a := a + 1
   until a < 20 + 2 - 3 / 4 / 5;
end.
