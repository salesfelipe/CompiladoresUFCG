program exFunction;
var
   a, b, ret : integer;

function max(num1, num2: integer): integer;
var
   result: integer;
begin
   if (num1 > num2) then
      result := num1

   else
      c := num2;
   max := result;
end;

begin
   a := 'teste';
   b := 'teste';
   (* this should throw an error, since the type of the parameters are different from the definition of the function *)
   max(a, b);

end.
