import { useEffect, useState } from "react";
import {
  getNotices,
  createNotice,
  updateNotice,
  deleteNotice,
} from "../api/noticeApi";
import type { NoticeResponse, NoticeRequest } from "../api/noticeApi";

function NoticePage() {
  const [notices, setNotices] = useState<NoticeResponse[]>([]);
  const [form, setForm] = useState<NoticeRequest>({ title: "", content: "" });
  const [editId, setEditId] = useState<number | null>(null);

  useEffect(() => {
    getNotices().then((res) => setNotices(res.data));
  }, []);

  const handleSubmit = async () => {
    if (editId !== null) {
      await updateNotice(editId, form);
    } else {
      await createNotice(form);
    }
    setForm({ title: "", content: "" });
    setEditId(null);
    getNotices().then((res) => setNotices(res.data));
  };

  const handleEdit = (notice: NoticeResponse) => {
    setEditId(notice.id);
    setForm({ title: notice.title, content: notice.content });
  };

  const handleDelete = async (id: number) => {
    await deleteNotice(id);
    getNotices().then((res) => setNotices(res.data));
  };

  return (
    <div>
      <h2>공지사항 관리</h2>
      <input
        placeholder="제목"
        value={form.title}
        onChange={(e) => setForm({ ...form, title: e.target.value })}
      />
      <input
        placeholder="내용"
        value={form.content}
        onChange={(e) => setForm({ ...form, content: e.target.value })}
      />
      <button onClick={handleSubmit}>
        {editId !== null ? "수정" : "생성"}
      </button>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>제목</th>
            <th>내용</th>
            <th>생성일</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody>
          {notices.map((notice) => (
            <tr key={notice.id}>
              <td>{notice.id}</td>
              <td>{notice.title}</td>
              <td>{notice.content}</td>
              <td>{notice.createdAt}</td>
              <td>
                <button onClick={() => handleEdit(notice)}>수정</button>
                <button onClick={() => handleDelete(notice.id)}>삭제</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default NoticePage;
