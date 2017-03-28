program exProcedure;
var
   a, b, c,  min: integer;
   d : string;
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
   findMin(a, b, c, d); (* Procedure is called using parameters of different type *)

end.
