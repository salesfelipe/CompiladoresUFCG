program exContinue;
var
   a: integer;

begin
   a := 10;
   (* repeat until loop execution *)
   repeat
      if( a = 15) then

      begin
         (* skip the iteration *)
         a := a + 1;
      end;

      a := a+1;
   until ( a = 20 );
end.
