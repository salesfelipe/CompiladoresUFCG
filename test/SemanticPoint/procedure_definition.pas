program exProcedure;
var
   a, b, c,  min: integer;
procedure findMin(x, y, z: integer; var m: integer);

begin
   if x < y then
      m:= x
   else
      m:= y;

   if z < m then
      m:= z;
end;

begin
   findadMin(a, b, c, min); (* Procedure call *)

end.
