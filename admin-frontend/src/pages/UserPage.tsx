import { useEffect, useState } from "react";
import { getUsers, createUser, updateUser, deleteUser } from "../api/userApi";
import type { UserResponse, UserRequest } from "../api/userApi";

function UserPage() {
  const [users, setUsers] = useState<UserResponse[]>([]);
  const [form, setForm] = useState<UserRequest>({
    username: "",
    email: "",
    phoneNumber: "",
  });
  const [editId, setEditId] = useState<number | null>(null);

  useEffect(() => {
    getUsers().then((res) => setUsers(res.data));
  }, []);

  const handleSubmit = async () => {
    if (editId !== null) {
      await updateUser(editId, form);
    } else {
      await createUser(form);
    }
    setForm({ username: "", email: "", phoneNumber: "" });
    setEditId(null);
    getUsers().then((res) => setUsers(res.data));
  };

  const handleEdit = (user: UserResponse) => {
    setEditId(user.id);
    setForm({
      username: user.username,
      email: user.email,
      phoneNumber: user.phoneNumber,
    });
  };

  const handleDelete = async (id: number) => {
    await deleteUser(id);
    getUsers().then((res) => setUsers(res.data));
  };

  return (
    <div>
      <h2>유저 관리</h2>
      <input
        placeholder="이름"
        value={form.username}
        onChange={(e) => setForm({ ...form, username: e.target.value })}
      />
      <input
        placeholder="이메일"
        value={form.email}
        onChange={(e) => setForm({ ...form, email: e.target.value })}
      />
      <input
        placeholder="전화번호"
        value={form.phoneNumber}
        onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })}
      />
      <button onClick={handleSubmit}>
        {editId !== null ? "수정" : "생성"}
      </button>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>이름</th>
            <th>이메일</th>
            <th>전화번호</th>
            <th>관리</th>
          </tr>
        </thead>

        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.phoneNumber}</td>
              <td>
                <button onClick={() => handleEdit(user)}>수정</button>
                <button onClick={() => handleDelete(user.id)}>삭제</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
export default UserPage;
