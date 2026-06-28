import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav>
      <Link to="/users">유저 관리</Link>
      {" | "}
      <Link to="/notices">공지사항 관리</Link>
    </nav>
  );
}

export default Navbar;
