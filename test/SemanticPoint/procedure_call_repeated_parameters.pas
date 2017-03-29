program exProcedure;
var
   a, b, c,  min: integer;
	 s : string;
procedure findMax(x, y, z: integer; var m: integer; m : string);
                                     (* m is declared twice *)
begin
   if x < y then
      m:= x
   else
      m:= y;

   if z < m then
      m:= z;
end;

begin
  findMax(a, b, c, min);

end.
